package services;

import models.Announcements;
import models.dto.CreateAnnouncementDto;
import models.dto.UpdateAnnouncementDto;
import repository.AnnouncementRepository;

public class AnnouncementService extends BaseService<Announcements, CreateAnnouncementDto, UpdateAnnouncementDto> {

    public AnnouncementService() {
        super(new AnnouncementRepository());
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
}
