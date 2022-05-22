package net.onlex;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class MyClient {
    
    @Id
    public UUID id;
    @Version
    public Integer version;
    
    public String name;
}
