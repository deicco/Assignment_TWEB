// swagger.js
const swaggerJSDoc = require('swagger-jsdoc');

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Express Mongo API',
            version: '1.0.0',
            description: 'API Central Server for movie and review data',
        },
        servers: [
            {
                url: 'http://localhost:3000',
            },
        ],
    },
    apis: ['./routes/*.js', './models/*.js'],
};

module.exports = swaggerJSDoc(options);