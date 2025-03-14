package com.scouting_app_2025.Bluetooth;

import static com.scouting_app_2025.MainActivity.TAG;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.scouting_app_2025.JSON.MurmurHash;
import com.scouting_app_2025.JSON.UpdateScoutingInfo;
import com.scouting_app_2025.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Locale;

public class BluetoothConnectedThread extends Thread {
    private final BluetoothSocket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private ByteBuffer byteBuffer;
    private byte[] buffer;
    private final String ack = "ACK";
    private int timeoutMs = 10000;
    private final byte[] byteAck = ack.getBytes(StandardCharsets.UTF_8);
    /**
     * @Info:
     */
    public BluetoothConnectedThread(BluetoothSocket socket) {
        this.socket = socket;

        //creates temporary input and output stream objects
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = socket.getInputStream();
        }
        catch(IOException e) {
            Log.e(TAG, "Input Stream Error: ", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        }
        catch(IOException e) {
            Log.e(TAG, "Output Stream Error: ", e);
        }

        //sets actual variables to temp versions
        inputStream = tmpIn;
        outputStream = tmpOut;
        ((MainActivity)MainActivity.context).setConnectedThread(this);
    }

    @Override
    public void run() {

    }

    private void resetByteBuffer(int capacity) {
        byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    /**
     * @Info: Only reads and stores the next message in {@code buffer}. {@code numBytes}
     * is used to set the length of {@code buffer}, not to read that amount of bytes.
     * */
    private void read(int numBytes) throws CommErrorException {
        buffer = new byte[numBytes];
        int bytesRead = 0;
        long readStart = Calendar.getInstance(Locale.US).getTimeInMillis();
        try {
            while (bytesRead == 0) {
                bytesRead = inputStream.read(buffer);
            }
            if((Calendar.getInstance(Locale.US).getTimeInMillis() - readStart) > timeoutMs) {
                throw new CommErrorException();
            }
        } catch (IOException e) {
            Log.e(TAG, "Failure to read: " + e);
            throw new CommErrorException();
        }
    }
    /**
     * @Info: Called to read specifically an {@code "ack"} and
     * throws an error if {@code read()} fails or ack is incorrect
     */
    private void readAck() throws CommErrorException {
        read(3);

        String message = new String(buffer, StandardCharsets.UTF_8);
        if(!message.equals(ack)) {
            throw new CommErrorException();
        }
    }
    /**
     * @Info: Called to read specifically an {@code "ack"} and
     * throws an error if {@code read()} fails or ack is incorrect
     */
    private void sendAck() throws CommErrorException {
        write(byteAck);
    }

    /**
     * @Info: Just write a {@code byte[]} to central computer
     */
    private void write(byte[] bytes) throws CommErrorException{
        try {
            outputStream.write(bytes);
        }
        catch(IOException e) {
            Log.e(TAG, "Failure to write: " + e);
            throw new CommErrorException();
        }
    }

    /**
     * @param code used to specify what information is going to be sent or received <p>
     * &nbsp;&nbsp;1 - send match data<p>
     * &nbsp;&nbsp;2 - send tablet information<p>
     *     -1 - check if lists of teams and matches are up to date <p>
     *     -2 - update lists of teams and matches <p>
     *      {@code IMPORTANT} numbers -1 and -2 shouldn't be used with this function.
     *             Use {@link BluetoothConnectedThread#checkLists()}  and {@link BluetoothConnectedThread#updateLists()} instead as needed
     * @Info: sends information
     *
     */
    public void sendInformation(byte[] bytes, int code) {
        try {
            write(new byte[]{(byte)code});
            readAck();
            resetByteBuffer(4);
            write(byteBuffer.putInt(bytes.length).array());
            readAck();
            write(bytes);
        }
        catch(CommErrorException e) {
            Log.e(TAG, "Communication exchange failed");
            cancel();
        }
    }
    /**
     * @Info:
     * @return Returns true if the data is up to date with the central computer
     * and false if there is a difference;
     */
    public boolean checkLists() {
        int byteLength;
        try {
            write(new byte[]{-1});
            read(4);

            resetByteBuffer(4);
            byteLength = byteBuffer.put(buffer).getInt(0);
            sendAck();

            resetByteBuffer(byteLength);
            read(byteLength);
            sendAck();

            Log.d(TAG, "Murmur Hash: \"" + MurmurHash.makeHash((new UpdateScoutingInfo()).getDataFromFile().getBytes(StandardCharsets.UTF_8)) + "\"");

            return byteBuffer.put(buffer).getInt(0) ==
                    MurmurHash.makeHash((new UpdateScoutingInfo()).getDataFromFile().getBytes(StandardCharsets.UTF_8));
        }
        catch(CommErrorException e) {
            Log.e(TAG, "Communication exchange failed");
            cancel();
            return false;
        }
    }
    /**
     * @Info:
     */
    public void updateLists() {
        int listLength;
        try {
            write(new byte[]{-2});

            resetByteBuffer(4);
            read(4);
            listLength = byteBuffer.put(buffer).getInt(0);
            sendAck();
            read(listLength);
            sendAck();

            (new UpdateScoutingInfo()).saveToFile(new String(buffer, StandardCharsets.UTF_8));
        }
        catch(CommErrorException | IOException e) {
            Log.e(TAG, "Communication exchange failed");
            cancel();
        }

    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    //used to flush stream and close socket
    public void cancel() {
        try {
            outputStream.flush();
            socket.close();
            ((MainActivity)MainActivity.context).setConnectivity(false);
        }
        catch(IOException e) {
            Log.e(TAG, "failed flush stream and close socket: ", e);
        }
    }
}
