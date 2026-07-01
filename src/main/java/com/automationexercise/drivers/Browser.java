package com.automationexercise.drivers;

public enum Browser {
    CHROME {
        @Override
        public AbstractDriver getFactory() {
            return new ChromeFactory();
        }
    },
    EDGE {
        @Override
        public AbstractDriver getFactory() {
            return new EdgeFactory();
        }
    },
    FIREFOX {
        @Override
        public AbstractDriver getFactory() {
            return new FirefoxFactory();
        }
    };

    public abstract AbstractDriver getFactory() ;

    }

