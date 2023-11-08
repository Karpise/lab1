package repository;

import exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.airport.model.Passenger;

import java.util.List;

@Repository
public abstract class PassengerRepositoryH2 implements PassengerRepository {

    private static final String CREATE = """
                        insert into passengers (passenger_id, passport, benefit)
                        values (:passengerId, :passport, :benefit)
            """;

    private final RowMapper<Passenger> rowMapper = new DataClassRowMapper<>(Passenger.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PassengerRepositoryH2(JdbcTemplate jdbcTemplate,
                            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Passenger read(Long id) {
        try {
            return jdbcTemplate.queryForObject("select * from passengers where passeneger_id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Passenger with id = [" + id + "] not found", e);
        }
    }

    @Override
    public List<Passenger> readAll() {
        return jdbcTemplate.query("select * from passenegers", rowMapper);
    }

    @Override
    public void create(Passenger passenger) {
        BeanPropertySqlParameterSource paramsSource = new BeanPropertySqlParameterSource(passenger);
        namedParameterJdbcTemplate.update(CREATE, paramsSource);
    }
}