package net.onlex;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ClientRepository implements PanacheRepositoryBase<MyClient, UUID> {
   
}
