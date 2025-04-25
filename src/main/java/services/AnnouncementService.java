package services;

import models.Announcements;
import models.dto.CreateAnnouncementDto;
import models.dto.UpdateAnnouncementDto;
import repository.AnnouncementRepository;

public class AnnouncementService {
    private AnnouncementRepository announcementRepository;

    public AnnouncementService() {
        this.announcementRepository = new AnnouncementRepository();
    }

    public Announcements getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Id does not exist!");
        }
        Announcements announcement = this.announcementRepository.getById(id);
        if (announcement == null) {
            throw new Exception("Announcement with Id: " + id + " does not exist!");
        }
        return announcement;
    }

    public Announcements create(CreateAnnouncementDto createAnnouncement) throws Exception {
        if (createAnnouncement.getCourseId() <= 0 || createAnnouncement.getTitle().isEmpty() || createAnnouncement.getContent().isEmpty() || createAnnouncement.getCreatedAt() == null) {
            throw new Exception("All fields are required!");
        }

        Announcements announcement = this.announcementRepository.create(createAnnouncement);
        if (announcement == null) {
            throw new Exception("Announcement could not be created!");
        }
        return announcement;
    }

    public Announcements update(UpdateAnnouncementDto updateAnnouncement) throws Exception {
        if (updateAnnouncement.getId() <= 0) {
            throw new Exception("Invalid announcement ID!");
        }

        Announcements existingAnnouncement = this.getById(updateAnnouncement.getId());
        if (existingAnnouncement == null) {
            throw new Exception("Announcement with Id: " + updateAnnouncement.getId() + " does not exist!");
        }

        Announcements updatedAnnouncement = this.announcementRepository.update(updateAnnouncement);
        if (updatedAnnouncement == null) {
            throw new Exception("Announcement could not be updated!");
        }
        return updatedAnnouncement;
    }
}
