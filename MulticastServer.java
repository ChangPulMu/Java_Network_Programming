/* Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved. */ 

public class MulticastServer {
    public static void main(String[] args) throws java.io.IOException {
        new MulticastServerThread().start();
    }
}
