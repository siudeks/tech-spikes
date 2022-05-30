package net.onlex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class MyClient {
    
    @Id
    public Long id;
    @Version
    public Integer version;
    
    public String name;
}
