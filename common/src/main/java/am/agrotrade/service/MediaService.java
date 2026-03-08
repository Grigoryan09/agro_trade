package am.agrotrade.service;

import am.agrotrade.model.Media;

import java.util.List;
import java.util.Optional;

public interface MediaService {

    void save(Media media);

    void delete(long mediaId);

    List<Media> findAll();

    Optional<Media> findById(long mediaId);
}
