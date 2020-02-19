import { Client } from "@microsoft/microsoft-graph-client";
import * as MicrosoftGraph from "@microsoft/microsoft-graph-types"

function getAuthenticatedClient(accessToken: string) {
  // Initialize Graph client

  const client = Client.init({
    // Use the provided access token to authenticate
    // requests
    authProvider: done => {
      done(null, accessToken);
    }
  });

  return client;
}

export async function getUserDetails(accessToken: string) {
  const client = getAuthenticatedClient(accessToken);

  const user = await client.api("/me").get();
  return user;
}

export async function getEvents(accessToken: string) {
  const client = getAuthenticatedClient(accessToken);

  const { value } = await client
    .api("/me/events")
    .select("subject,organizer,start,end")
    .orderby("createdDateTime DESC")
    .get()

  return value as EventInfo[];
}

export type EventInfo = Pick<MicrosoftGraph.Event, 'id' | 'subject' | 'organizer' | 'start' | 'end'>;
