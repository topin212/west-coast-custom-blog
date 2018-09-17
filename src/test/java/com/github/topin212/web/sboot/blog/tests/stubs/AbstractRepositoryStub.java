package com.github.topin212.web.sboot.blog.tests.stubs;

import com.github.topin212.web.sboot.blog.entities.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepositoryStub<T> {

    List<T> storage;


    public List<T> findAll() {
        return storage;
    }

    public List<T> findAll(Sort sort) {
        return null;
    }

    public List<T> findAllById(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void deleteById(Long aLong) {

    }

    public void delete(T blogPost) {

    }

    public void deleteAll(Iterable<? extends T> iterable) {

    }

    public void deleteAll() {

    }


    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    public Optional<T> findById(Long id) {
        if(id.intValue() < storage.size())
            return Optional.of(storage.get(id.intValue()));
        return Optional.empty();
    }

    public boolean existsById(Long id) {
        if(id.intValue() > storage.size())
            return false;
        return storage.get(id.intValue()) != null;
    }

    public void flush() {
        storage = new ArrayList<>();
    }

    public <S extends T> S saveAndFlush(S s) {
        return null;
    }

    public void deleteInBatch(Iterable<T> iterable) {

    }

    public void deleteAllInBatch() {

    }

    public T getOne(Long aLong) {
        return null;
    }

    public <S extends T> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    public List<T> findByPublisher(Publisher publisher) {
        return null;
    }

    public Page<T> findByPublisher(Publisher publisher, Pageable pageable) {
        return null;
    }
}
