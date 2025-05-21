package services;

import models.StudentAccepted;
import models.TransferRequest;
import repository.TransferRequestRepository;
import repository.StudentAcceptedRepository;

public class TransferService {
    private final StudentAcceptedRepository studentRepository;
    private final TransferRequestRepository transferRepository;

    public TransferService() {
        this.studentRepository = new StudentAcceptedRepository();
        this.transferRepository = new TransferRequestRepository();
    }

    public StudentAccepted getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public boolean submitTransferRequest(String id, String name, String program, String targetProgram) {
        if (targetProgram == null || targetProgram.isEmpty()) {
            return false;
        }
        TransferRequest request = new TransferRequest(id, name, program, targetProgram);
        return transferRepository.save(request);
    }

    public String getCurrentUserId() {
        // Replace with actual logic (e.g., session, OAuth)
        return "123456"; // Example ID
    }
}