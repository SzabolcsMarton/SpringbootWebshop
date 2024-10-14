package com.szabolcs.SpringbootWebshop.Dto;

public record RegisterUserDto(String firstName, String lastName, String email, String address , String passwordOne, String passwordTwo) {
}
