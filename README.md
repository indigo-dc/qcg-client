# qcg-client

This is a simple Java project for communicating with QCG API.

The api is delivered as Java class QcgApi with 4 methods:

* QcgResources GetResources()
* QcgJob StartJob(String command)
* QcgJobs GetJobs()
* QcgJob GetJobInfo(int jobId)

The class contains also the method testApi() with examples of use.
