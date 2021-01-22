package com.example.demo.repositories;

import com.example.demo.entities.Cast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepo extends CrudRepository<Cast, Integer> {
}
