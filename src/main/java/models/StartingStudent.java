package models;

public class StartingStudent {
        private int id;
        private String name;
        private String surname;
        private String address;
        private int age;
        private String gpaTranscript;
        private String ethnicity;
        private String extraCreditDocument;
        private double testScore;
        private int acceptanceTestScore;
        private String program;
        private double gradeAverage;
        private double extraPoints;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getGpaTranscript() { return gpaTranscript; }
        public void setGpaTranscript(String gpaTranscript) { this.gpaTranscript = gpaTranscript; }

        public String getEthnicity() { return ethnicity; }
        public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

        public String getExtraCreditDocument() { return extraCreditDocument; }
        public void setExtraCreditDocument(String extraCreditDocument) { this.extraCreditDocument = extraCreditDocument; }

        public double getTestScore() { return testScore; }
        public void setTestScore(double testScore) { this.testScore = testScore; }

        public int getAcceptanceTestScore() { return acceptanceTestScore; }
        public void setAcceptanceTestScore(int acceptanceTestScore) { this.acceptanceTestScore = acceptanceTestScore; }

        public String getProgram() { return program; }
        public void setProgram(String program) { this.program = program; }

        public double getGradeAverage() { return gradeAverage; }
        public void setGradeAverage(double gradeAverage) { this.gradeAverage = gradeAverage; }

        public double getExtraPoints() { return extraPoints; }
        public void setExtraPoints(double extraPoints) { this.extraPoints = extraPoints; }

        public double getCalculatedScore() {
                // Formula: 0.3 * grade_average + 0.3 * test_score + 0.4 * acceptance_test_score + 0.1 * extra_points
                // Assuming grade_average (0-5), test_score (0-100), acceptance_test_score (0-100), extra_points (0-10)
                // Normalize to percentage (0-100)
                double maxGrade = 5.0;
                double maxExtraPoints = 10.0;
                double normalizedGrade = (gradeAverage / maxGrade) * 100.0;
                double normalizedExtraPoints = (extraPoints / maxExtraPoints) * 100.0;
                return (0.3 * normalizedGrade) + (0.3 * testScore) + (0.4 * acceptanceTestScore) + (0.1 * normalizedExtraPoints);
        }

        public String getStatus() {
                return getCalculatedScore() >= 70.0 ? "Accepted" : "Rejected";
        }
}