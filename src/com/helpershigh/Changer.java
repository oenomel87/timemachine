package com.helpershigh;

public interface Changer {
    static Changer getChanger() {
        return new ChangerImpl();
    }

    String changeOperator(String filePathStr, String targetTimeStr);
}
