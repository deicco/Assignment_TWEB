// swagger.js
const swaggerJSDoc = require('swagger-jsdoc');

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Dataset Management API (MongoDB)',
            version: '1.0.0',
            description: 'Microservizio Express dedicato alla fornitura di dati dinamici (recensioni) e riferimenti ai film.',
        },
        servers: [
            {
                url: 'http://localhost:3001',
                description: 'Dataset Server (Express)'
            },
        ],
    },

    apis: ['./routes/*.js', './models/*.js'],
};

module.exports = swaggerJSDoc(options);