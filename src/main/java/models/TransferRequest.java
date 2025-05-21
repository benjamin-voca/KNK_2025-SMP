package models;

public class TransferRequest {
    private String id;
    private String name;
    private String program;
    private String targetProgram;


    public TransferRequest(String id, String name, String program, String targetProgram) {
        this.id = id;
        this.name = name;
        this.program = program;
        this.targetProgram = targetProgram;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getTargetProgram() {
        return targetProgram;
    }

    public void setTargetProgram(String targetProgram) {
        this.targetProgram = targetProgram;
    }
}