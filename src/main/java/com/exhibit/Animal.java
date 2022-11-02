package com.exhibit;

public enum Animal {
    CAT {
        public String makeNoise(int i) { return "MEOW!"; }
    },
    DOG {
        public String makeNoise(int i) { return "WOOF!"; }
    };

    public abstract String makeNoise(int i);
}
