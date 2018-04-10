package com.danil.etl;

public class ChunkTransformNotNeededException extends Exception {

    public ChunkTransformNotNeededException() {
        super();
    }

    public ChunkTransformNotNeededException(String message, Exception ex) {
        super(message, ex);
    }
}
