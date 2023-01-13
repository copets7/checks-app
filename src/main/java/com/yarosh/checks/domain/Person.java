package com.yarosh.checks.domain;

import java.util.Objects;

public class Person {

    private Long id;
    private String personName;
    private DiscountCard card;

    public Person(Long id, String personName, DiscountCard card) {
        this.id = id;
        this.personName = personName;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public DiscountCard getCard() {
        return card;
    }

    public void setCard(DiscountCard card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(personName, person.personName) && Objects.equals(card, person.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personName, card);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personName='" + personName + '\'' +
                ", card=" + card +
                '}';
    }
}
