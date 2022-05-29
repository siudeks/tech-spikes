package net.onlex;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
    
    @Id
    public int id;
    public String name;
}
