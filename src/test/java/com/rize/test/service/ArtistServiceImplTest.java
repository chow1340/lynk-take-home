package com.rize.test.service;

import com.rize.test.TestApplication;
import com.rize.test.model.Artist;
import com.rize.test.respository.ArtistRepository;
import com.rize.test.utilities.artist.ResponseMessage;
import com.rize.test.utilities.artist.ResponseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        TestApplication.class,
})
@ActiveProfiles("test")

class ArtistServiceImplTest {

    @Autowired
    private ArtistRepository artistRepository;
    ArtistServiceImpl artistService;

    @BeforeEach
    void setup(){
        artistRepository.deleteAll();
        artistService = new ArtistServiceImpl(artistRepository);
    }

    @Test
    public void saveArtist(){
        Artist artist = new Artist("TEST_NAME", "LASTNAME", new Date(1997, 10, 3), "TESTEMAIL@gmail.com", "ARTIST", "","");
        ResponseMessage responseMessage = artistService.saveArtist(artist);
        assert responseMessage.getStatus().equals(ResponseStatus.SUCCESS);
        assert artistRepository.findAll().size() == 1;
    }

}