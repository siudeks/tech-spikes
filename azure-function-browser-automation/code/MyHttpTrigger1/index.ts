import { AzureFunction, Context, HttpRequest } from "@azure/functions"
import { myAction } from './action'

const httpTrigger: AzureFunction = async function (context: Context, req: HttpRequest): Promise<void> {
    context.log('HTTP trigger function processed a request.');
    const name = (req.query.name || (req.body && req.body.name));

    if (name) {
        await myAction();

        context.res = {
            // status: 200, /* Defaults to 200 */
            body: "Hello 1 " + (req.query.name || req.body.name)
        };
    }
    else {
        context.res = {
            status: 400,
            body: "Please pass a name on the query string or in the request body"
        };
    }
};

export default httpTrigger;
