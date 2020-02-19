import React, { useState, useEffect } from "react";
import { Table } from "reactstrap";
import moment from "moment";
import appConfig from "./Config";
import { getEvents } from "./GraphService";
import * as Msal from "msal";
// We have to import explicitly
// https://github.com/microsoftgraph/msgraph-sdk-javascript/issues/230
import { ImplicitMSALAuthenticationProvider } from "@microsoft/microsoft-graph-client/lib/src/ImplicitMSALAuthenticationProvider";
import { from } from "rxjs";
import { flatMap, map } from "rxjs/operators";

function formatDateTime(dateTime) {
  return moment
    .utc(dateTime)
    .local()
    .format("M/D/YY h:mm A");
}

export const Calendar: React.FC<{}> = props => {
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const msalConfig: Msal.Configuration = {
      auth: {
        clientId: appConfig.appId,
        redirectUri: appConfig.redirectUri
      }
    };
    const graphScopes = appConfig.scopes;
    const agent = new Msal.UserAgentApplication(msalConfig);
    const options = { scopes: graphScopes };
    const authProvider = new ImplicitMSALAuthenticationProvider(agent, options);
    const params: Msal.AuthenticationParameters = {};

    var subscription = from(agent.acquireTokenSilent(params))
      .pipe(
        // Get the user's events
        map(it => getEvents(it))
      )
      .subscribe(it => {
        // Update the array of events in state
        setEvents({ events: events.value });
      });

      // TODO return subscription.unsubscribe;
  });

  return (
    <div>
      <h1>Calendar</h1>

      <Table>
        <thead>
          <tr>
            <th scope="col">Organizer</th>
            <th scope="col">Subject</th>
            <th scope="col">Start</th>
            <th scope="col">End</th>
          </tr>
        </thead>
        <tbody>
          {this.state.events.map(function(event) {
            return (
              <tr key={event.id}>
                <td>{event.organizer.emailAddress.name}</td>
                <td>{event.subject}</td>
                <td>{formatDateTime(event.start.dateTime)}</td>
                <td>{formatDateTime(event.end.dateTime)}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </div>
  );
};
