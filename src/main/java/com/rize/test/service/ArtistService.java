package com.rize.test.service;

import com.rize.test.model.Artist;
import com.rize.test.utilities.artist.ResponseMessage;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    ResponseMessage saveArtist(Artist artist);

    ResponseMessage deleteArtist(Integer id);

    List<Artist> getAll(String search, String category, Integer month);

    Optional<Artist> findById(Integer id);

}
