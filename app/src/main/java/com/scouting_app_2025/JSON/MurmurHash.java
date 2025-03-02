package com.scouting_app_2025.JSON;

public class MurmurHash {
    public static int makeHash(byte[] data) {

        final int multiply = 0x5bd1e995;
        final int rotate = 24;
        int hash = 1138 ^ data.length;

        int index = 0;
        while(data.length - index >= 4){
            int current;

            current = data[index];
            current |= data[1 + index] << 8;
            current |= data[2 + index] << 16;
            current |= data[3 + index] << 24;

            current *= multiply;
            current ^= current >> rotate;
            current *= multiply;

            hash *= multiply;
            hash ^= current;

            index += 4;
        }

        switch(data.length - index) {
            case 3: hash ^= data[2 + index] << 16;
            case 2: hash ^= data[1 + index] << 8;
            case 1: hash ^= data[index];
                    hash *= multiply;
        }

        hash ^= hash >> 13;
        hash *= multiply;
        hash ^= hash >> 15;

        return hash;
    }
}
