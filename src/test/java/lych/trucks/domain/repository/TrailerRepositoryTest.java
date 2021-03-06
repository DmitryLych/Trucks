package lych.trucks.domain.repository;

import lych.trucks.domain.model.Trailer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrailerRepositoryTest {

    @Autowired
    private TrailerRepository trailerRepository;

    private static final Integer TRUCK_ID_CONTENT = 1;

    private static final String REGISTER_SIGN_CONTENT = "sign";

    private static final Integer VOLUME_CONTENT = 1243;

    private static final String TYPE_CONTENT = "type";

    @Before
    public void setUp() {

        final Trailer trailer = new Trailer();

        trailer.setTrailerFk(TRUCK_ID_CONTENT);
        trailer.setRegisterSign(REGISTER_SIGN_CONTENT);
        trailer.setTrailerType(TYPE_CONTENT);
        trailer.setVolume(VOLUME_CONTENT);

        trailerRepository.save(trailer);
    }

    @Test
    public void findByTrailerFk() {

        final Trailer foundTrailer = trailerRepository.findByTrailerFk(TRUCK_ID_CONTENT);

        assertThat(foundTrailer.getTrailerFk(), is(TRUCK_ID_CONTENT));
    }

    @Test
    public void findByRegisterSign() {

        final Trailer foundTrailer = trailerRepository.findByRegisterSign(REGISTER_SIGN_CONTENT);

        assertThat(foundTrailer.getRegisterSign(), is(REGISTER_SIGN_CONTENT));
    }

    @Test
    public void findByVolume() {

        final List<Trailer> foundTrailers = trailerRepository.findByVolume(VOLUME_CONTENT);

        foundTrailers.forEach(trailer -> assertThat(trailer.getVolume(), is(VOLUME_CONTENT)));
    }

    @Test
    public void findByType() {

        final List<Trailer> foundTrailers = trailerRepository.findByType(TYPE_CONTENT);

        foundTrailers.forEach(trailer -> assertThat(trailer.getTrailerType(), is(TYPE_CONTENT)));
    }
}
