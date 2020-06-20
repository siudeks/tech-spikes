# WIP

## WebBrowser automation for a hardcoded (but parametrized) scenario

Sometimes we need to conduct a scenario using automated web browser. Actually when we have defined input and output of operationa, then challlenge is to automate web browser to obtain final values.

I would like to avoid using Selenium because it is error prone (according to my experience), but rather using [Puppeteer](https://github.com/puppeteer/puppeteer) which should be async, fast and - in my case - supported by TypeScript.

My spike covers using [dockerized puppeter](https://github.com/ltwlf/azure-functions-docker-puppeteer)

## prerequisits
- installed [azure function core tools](https://docs.microsoft.com/en-us/azure/azure-functions/functions-run-local). I use v2.  
  In my case finally I had to add [extra switches](https://github.com/npm/npm/issues/17268#issuecomment-310167614)

How to run the spike

- Step 1 - make your function working
 - set context to [code](code) folder
 - **npm **


- **az login** to connect to Azure
- **terraform init / apply** to create structure

## Used / inspired by
- https://www.merrell.dev/posts/2018/09/28/azure-web-apps-for-containers-using-terraform/
- https://medium.com/@bogdanbujdea/running-puppeteer-in-azure-container-instances-b24fb0a8d3e
