package com.location.amateolocationengin.adapters;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamOperations {
    public static String InputStreamToString(InputStream inputStream, int i) {
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[i];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return sb.toString();
                }
                sb.append(new String(bArr, 0, read));
            } catch (IOException e) {
                throw new RuntimeException("Cannot convert stream to string", e);
            }
        }
    }

    public static String InputStreamToString(InputStream inputStream) {
        return InputStreamToString(inputStream, 1024);
    }
}
