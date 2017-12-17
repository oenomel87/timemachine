package com.helpershigh;

import java.util.Scanner;

public class UIImpl implements UI {

    private static final Scanner SCN = new Scanner(System.in);

    protected UIImpl() {}

    public void uiService() {
        while(true) {
            System.out.print("파일 경로를 입력해 주세요(종료-0): ");
            String filePath = UIImpl.SCN.next();
            if("0".equals(filePath)) {
                System.out.println("\n\nGood bye");
                break;
            }

            System.out.print("변경일(YYYY-MM-DD HH:mm:ss): ");
            String createDate = UIImpl.SCN.next() + " " + UIImpl.SCN.next();
            switch (Changer.getChanger().changeOperator(filePath, createDate)) {
                case "SUCCESS":
                    System.out.println("\n파일 생셩일이 변경되었습니다!\n");
                    break;
                case "NOTFOUND":
                    System.out.println("\n파일을 찾지 못했습니다. 경로를 확인해주세요.\n");
                    break;
                case "WRONG_DATETYPE":
                    System.out.println("\n잘못된 날짜형식입니다.\n");
                    break;
                case "FAIL":default:
                    System.out.println("\n파일 생셩일을 변경하지 못했습니다.\n");
                    break;
            }
        }
    }
}
