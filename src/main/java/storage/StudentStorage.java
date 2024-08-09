package storage;

import model.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StudentStorage {

    private Student[] array = new Student[10];
    private int size = 0;

    public void add(Student student) {
        if (size == array.length) {
            increaseArray();
        }
        array[size++] = student;
    }


    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.println(i + ". " + array[i]);
        }
    }

    private void increaseArray() {
        Student[] temp = new Student[array.length + 10];
        System.arraycopy(array, 0, temp, 0, size);
        array = temp;
    }

    public void deleteByIndex(int index) {
        if (index < 0 || index >= size) {
            System.out.println("invalid index");
        } else {
            for (int i = index; i < size; i++) {
                array[i] = array[i + 1];
            }
            size--;
        }
    }

    public void printStudentsByLessonName(String lessonName) {
        for (int i = 0; i < size; i++) {
            if (array[i].getLesson().equals(lessonName)) {
                System.out.println(array[i]);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Student getStudentByIndex(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[index];
    }

    public void writeStudentsToExcel(String fileDir) throws IOException {
        File directory = new File(fileDir);
        if (directory.isFile()) {
            throw new RuntimeException("fileDir must be directory! ");
        }
        File excelFile = new File(directory, "students_" + System.currentTimeMillis() + ".xlsx");
        excelFile.createNewFile();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("students");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0);

        Cell nameCell = headerRow.createCell(0);
        nameCell.setCellValue("name");

        Cell surnameCell = headerRow.createCell(1);
        surnameCell.setCellValue("surname");

        Cell ageCell = headerRow.createCell(2);
        ageCell.setCellValue("age");

        Cell phoneNumberCell = headerRow.createCell(3);
        phoneNumberCell.setCellValue("phone");

        for (int i = 0; i < size; i++) {
            Student student = array[i];
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(student.getName());
            row.createCell(1).setCellValue(student.getSurname());
            row.createCell(2).setCellValue(student.getAge());
            row.createCell(3).setCellValue(student.getPhoneNumber());
        }
        workbook.write(new FileOutputStream(excelFile));
        System.out.println("Excel was created successfully");
    }
}