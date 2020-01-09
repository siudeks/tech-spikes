# WIP

## WebBrowser automation for hardcoded scenario

Sometimes we need to conduct a scenario using webautomated browser. Actually when we have defined input and output of operationa, then challlenge is to automate web browser to obtain final values.

I would like to avoid using Selenium because it is error prone (according to my experience), but rather using [PuppeteerSharp](https://github.com/kblok/puppeteer-sharp) which should be async and fast.

I would like to use only Azure Function to avoid expensive keeping working webbrowser instances and to magane their lifecycle and queueing requests. I tried to use [puppeteer](https://www.npmjs.com/package/puppeteer) to automate browser as well as [puppeteer sharp](https://www.nuget.org/packages/PuppeteerSharp). Both solution do not work for some reasons.

Finally, in the spike I am testing [dockerized puppeter](https://github.com/ltwlf/azure-functions-docker-puppeteer)

How to run the spike

- **az login** to connect to Azure
- **terraform init / apply** to create structure

Used articles:
- https://www.merrell.dev/posts/2018/09/28/azure-web-apps-for-containers-using-terraform/

