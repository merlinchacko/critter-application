package com.udacity.jdnd.course3.critter.exception;

public class IdNotFoundException extends RuntimeException
{

    public IdNotFoundException(Long id)
    {
        super("Id - " + id + "not found");
    }
}
