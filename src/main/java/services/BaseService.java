package services;

import repository.BaseRepository;

public abstract class BaseService<TModel, TCreateDto, TUpdateDto> {
    protected BaseRepository<TModel, TCreateDto, TUpdateDto> repository;

    public BaseService(BaseRepository<TModel, TCreateDto, TUpdateDto> repository) {
        this.repository = repository;
    }

    public TModel getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Invalid ID provided.");
        }

        TModel model = repository.getById(id);
        if (model == null) {
            throw new Exception("Item with ID " + id + " does not exist.");
        }

        return model;
    }

    public TModel create(TCreateDto createDto) throws Exception {
        TModel model = repository.create(createDto);
        if (model == null) {
            throw new Exception("Item could not be created.");
        }

        return model;
    }

    public TModel update(TUpdateDto updateDto) throws Exception {
        // Assuming updateDto has a method getId()
        int id = (int) updateDto.getClass().getMethod("getId").invoke(updateDto);

        if (id <= 0) {
            throw new Exception("Invalid ID provided.");
        }

        TModel existing = getById(id);
        if (existing == null) {
            throw new Exception("Item with ID " + id + " does not exist.");
        }

        TModel updated = repository.update(updateDto);
        if (updated == null) {
            throw new Exception("Item could not be updated.");
        }

        return updated;
    }
}
