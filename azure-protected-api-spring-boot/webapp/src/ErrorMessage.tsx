import React from 'react';
import { Alert } from 'reactstrap';

interface ErrorMessageProps {
  debug: boolean;
  message: string;
}

export const ErrorMessage: React.SFC<ErrorMessageProps> = (props) => {

  let debug: any;

  if (props.debug) {
       debug = <pre className="alert-pre border bg-light p-2"><code>{props.debug}</code></pre>;
  }

  return (
      <Alert color="danger">
        <p className="mb-3">{props.message}</p>
        {debug}
      </Alert>
  )
}
