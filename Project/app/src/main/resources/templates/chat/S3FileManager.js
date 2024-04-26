const AWS = require('aws-sdk');
const fs = require('fs');

class S3FileManager {
  constructor(bucketName, endpoint, region, accessKey, secretKey) {
    this.bucketName = bucketName;
    this.endpoint = new AWS.Endpoint(endpoint);
    this.region = region;
    this.accessKey = accessKey;
    this.secretKey = secretKey;

    this.S3 = new AWS.S3({
      endpoint: this.endpoint,
      region: this.region,
      credentials: {
          accessKeyId: this.accessKey,
          secretAccessKey: this.secretKey
      }
    });
  }

  async uploadFile(uploadDir, fileName, filePath) {
    await this.S3.putObject({
      Bucket: this.bucketName,
      Key: uploadDir + fileName,
      Body: fs.createReadStream(filePath)
    }).promise();
    console.log("올라감");
  }

  async deleteFile(objectName) {
    await this.S3.deleteObject({
      Bucket: this.bucketName,
      Key: objectName
    }).promise();
  }
}
// Node.js는 다른 파일에서 클래스를 import 하려면 해당 클래스를 export 해주어야 한다.
module.exports = S3FileManager