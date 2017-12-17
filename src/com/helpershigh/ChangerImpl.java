package com.helpershigh;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ChangerImpl implements Changer {

    private final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected ChangerImpl() {}

    public String changeOperator(String filePathStr, String targetTimeStr) {
        Path filePath = Paths.get(filePathStr);
        if(Files.notExists(filePath)) {
            return "NOTFOUND";
        }
        if(!this.dateFormatChecker(targetTimeStr)) {
            return "WRONG_DATETYPE";
        }

        try {
            BasicFileAttributeView attributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
            attributes.setTimes(null, null, this.setNewCreationTime(filePath, targetTimeStr));
            long newCreationTime = ((FileTime)Files.getAttribute(filePath, "creationTime")).toMillis();
            System.out.println("변경된 날짜 " + this.getEpochTimeToDataTimeStr(newCreationTime));
        } catch (Exception ex) {
            ex.printStackTrace();
            return "FAIL";
        }

        return "SUCCESS";
    }

    private boolean dateFormatChecker(String dateStr) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateStr, this.FMT);
            return date.format(this.FMT).equals(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private FileTime setNewCreationTime(Path path, String dateStr) throws Exception {
        LocalDateTime date = LocalDateTime.parse(dateStr, this.FMT);
        long dateEpoch = date.atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli();
        return FileTime.fromMillis(dateEpoch);
    }

    private String getEpochTimeToDataTimeStr(long epochTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneId.of("Asia/Seoul")).format(this.FMT);
    }
}
