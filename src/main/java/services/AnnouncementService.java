package services;

import models.Announcements;
import models.dto.CreateAnnouncementDto;
import models.dto.UpdateAnnouncementDto;
import repository.AnnouncementRepository;

import java.util.List;

public class AnnouncementService extends BaseService<Announcements, CreateAnnouncementDto, UpdateAnnouncementDto> {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService() {
        super(new AnnouncementRepository());
        this.announcementRepository = new AnnouncementRepository();
    }

    @Override
    public Announcements getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Announcements create(CreateAnnouncementDto createAnnouncement) throws Exception {
        return super.create(createAnnouncement);
    }

    @Override
    public Announcements update(UpdateAnnouncementDto updateAnnouncement) throws Exception {
        return super.update(updateAnnouncement);
    }

    public List<String> getAnnouncementsByProfessorId(int professorId) {
        return announcementRepository.findAnnouncementsByProfessorId(professorId);
    }
}
