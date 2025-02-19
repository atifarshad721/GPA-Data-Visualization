public class Student {
    
    private String name;
    private String rollNo;
    double[] sgpa;
    private double cgpa;
    private static int objectCount = 0;

    private static final int TOTAL_SEMESTERS = 3;
    private static final String VALID_ROLL_NO = "[FS]\\d{2}BDOCS\\d[ME]\\d{5}";

    Student(String name, String rollNo, double sgpa1, double sgpa2, double sgpa3){

        if(!isValidRollNo(rollNo)){
            throw new IllegalArgumentException("Roll no. is invalid!");
        }

        this.name = name;
        this.rollNo = rollNo;
        this.sgpa = new double[TOTAL_SEMESTERS];
        this.sgpa[0] = sgpa1;
        this.sgpa[1] = sgpa2;
        this.sgpa[2] = sgpa3;
        this.cgpa = Math.round(((sgpa1 + sgpa2 + sgpa3) / TOTAL_SEMESTERS) * 100.0)/100.0;

        objectCount++;
    }
    
    public String getName(){
        return name;
    }

    public String getRollNo(){
        return rollNo;
    }

    public double getCGPA(){
        return cgpa;
    }

    public double[] getSGPA(){
        return sgpa;
    }

    public static int getObjectCount() {
        return objectCount;
    }

    private static boolean isValidRollNo(String rollNo){
        return rollNo.matches(VALID_ROLL_NO);
    }

}
