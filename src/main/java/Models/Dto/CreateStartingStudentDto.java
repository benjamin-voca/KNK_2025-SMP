package Models.Dto;

public class CreateStartingStudentDto {
    
        private String name;
        private String surname;
        private int age;
        private String address;
        private double gradeAvg;
        private String ethnicity;
        private double extraCredit;
        private double testScore;
        private double acceptanceTestScore;
        private String program;

        public CreateStartingStudentDto(String name, String surname, int age, String address, double gradeAvg,
                                        String ethnicity, double extraCredit, double testScore,
                                        double acceptanceTestScore, String program) {
            this.name = name;
            this.surname = surname;
            this.age = age;
            this.address = address;
            this.gradeAvg = gradeAvg;
            this.ethnicity = ethnicity;
            this.extraCredit = extraCredit;
            this.testScore = testScore;
            this.acceptanceTestScore = acceptanceTestScore;
            this.program = program;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public double getGradeAvg() { return gradeAvg; }
        public void setGradeAvg(double gradeAvg) { this.gradeAvg = gradeAvg; }

        public String getEthnicity() { return ethnicity; }
        public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

        public double getExtraCredit() { return extraCredit; }
        public void setExtraCredit(double extraCredit) { this.extraCredit = extraCredit; }

        public double getTestScore() { return testScore; }
        public void setTestScore(double testScore) { this.testScore = testScore; }

        public double getAcceptanceTestScore() { return acceptanceTestScore; }
        public void setAcceptanceTestScore(double acceptanceTestScore) { this.acceptanceTestScore = acceptanceTestScore; }

        public String getProgram() { return program; }
        public void setProgram(String program) { this.program = program; }

        @Override
        public String toString() {
            return String.format("Student: %s %s | Age: %d | Program: %s", name, surname, age, program);
        }
    }