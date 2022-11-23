package com.rize.test.service;

import com.rize.test.model.Artist;
import com.rize.test.respository.ArtistRepository;
import com.rize.test.utilities.artist.ResponseMessage;
import com.rize.test.utilities.artist.ResponseStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Service
public class ArtistServiceImpl implements ArtistService {

    public static final int LIMIT = 1000;
    @PersistenceContext
    private EntityManager entityManager;
    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public ResponseMessage saveArtist(Artist artist) {
        artistRepository.save(artist);
        return new ResponseMessage(ResponseStatus.SUCCESS, "Artist Saved");
    }

    @Override
    public ResponseMessage deleteArtist(Integer id) {
        if(artistRepository.existsById(id)) {
            artistRepository.deleteById(id);
            return new ResponseMessage(ResponseStatus.SUCCESS, String.format("Deleted artist id: %d", id));
        } else {
            return new ResponseMessage(ResponseStatus.ERROR, String.format("Artist with id %d does not exist", id));
        }
    }

    @Override
    public Optional<Artist> findById(Integer id) {
        return artistRepository.findById(id);
    }

    @Override
    public List<Artist> getAll(String search, String category, Integer month) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> criteriaQuery = criteriaBuilder.createQuery(Artist.class);

        Root<Artist> artist = criteriaQuery.from(Artist.class);
        Predicate finalPredicate = null;

        if(search != null && !search.isEmpty()) {
            finalPredicate = buildNameSearch(search, criteriaBuilder, artist);
        }

        if(category != null && !category.isEmpty()) {
            finalPredicate = buildCategorySearch(category, criteriaBuilder, artist);
        }

        if(month != null) {
            finalPredicate = buildBirthdayMonthSearch(month, criteriaBuilder, artist);
        }

        if(finalPredicate != null) {
            criteriaQuery.where(finalPredicate);
        }

        return entityManager
                .createQuery(criteriaQuery.orderBy(criteriaBuilder.desc(artist.get("createdAt"))))
                .setMaxResults(LIMIT)
                .getResultList();

    }

    private Predicate buildBirthdayMonthSearch(Integer month, CriteriaBuilder criteriaBuilder, Root<Artist> artistSearch) {
        return criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, artistSearch.get("birthday")), month);
    }

    private Predicate buildCategorySearch(String category, CriteriaBuilder criteriaBuilder, Root<Artist> artistSearch) {
        return criteriaBuilder.equal(criteriaBuilder.lower(artistSearch.get("category")), category.toLowerCase());
    }

    private Predicate buildNameSearch(String search, CriteriaBuilder criteriaBuilder, Root<Artist> itemRoot) {
        Predicate firstName = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("firstName")), "%" + search.toLowerCase() + "%");
        Predicate lastName = criteriaBuilder.like(criteriaBuilder.lower(itemRoot.get("lastName")), "%" + search.toLowerCase() + "%");
        return criteriaBuilder.or(firstName, lastName);
    }

}
