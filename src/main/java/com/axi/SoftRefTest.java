package com.axi;

import lombok.Getter;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class SoftRefTest {
    private static SoftRefTest instance = new SoftRefTest();
    public static void main(String[] args) {
        for(int i = 0;; i++) {
            SoftRefTest.getInstance().cacheStudent(new Student(i,"name"+i));
        }
    }
    public static SoftRefTest getInstance() {
        return instance;
    }
    private static Map<Integer, studentRef> students;

    private static ReferenceQueue<Student> queue;

    private SoftRefTest() {
        students = new HashMap<>();
        queue = new ReferenceQueue<>();
    }

    public void clean() {
        studentRef r = null;
        while ((r = (studentRef)queue.poll()) != null) {
            students.remove(r._key);
        }
    }
    private void cacheStudent(Student em){
        clean();
        studentRef ref = new studentRef(em,queue);
        students.put(em.getId(),ref);
        System.out.println(students.size());
    }

    class studentRef extends SoftReference<Student> {
        private Integer _key = null;

        public studentRef(Student referent) {
            super(referent);
        }

        public studentRef(Student referent, ReferenceQueue<Student> q) {
            super(referent, q);
            _key = referent.getId();
        }
    }


}
class Student {
    @Getter
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
