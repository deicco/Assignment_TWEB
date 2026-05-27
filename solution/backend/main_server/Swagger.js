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
        paths: {
            "/": {
                "get": {
                    "summary": "Retrieve the home page",
                    "tags": ["Movies"],
                    "parameters": [
                        { "in": "query", "name": "page", "schema": { "type": "integer" }, "description": "Page number" },
                        { "in": "query", "name": "name", "schema": { "type": "string" }, "description": "Movie name" }
                    ],
                    "responses": {
                        "200": { "description": "Success" },
                        "500": { "description": "Server Error" }
                    }
                }
            },
            "/movie_detail": {
                "get": {
                    "summary": "Retrieve movie details",
                    "tags": ["Movies", "Reviews"],
                    "parameters": [
                        { "in": "query", "name": "id", "required": true, "schema": { "type": "integer" } }
                    ],
                    "responses": {
                        "200": { "description": "Success" },
                        "404": { "description": "Not found" }
                    }
                }
            },
            "/api/reviews": {
                "get": {
                    "summary": "Get paginated reviews",
                    "tags": ["Reviews"],
                    "parameters": [
                        { "in": "query", "name": "movieName", "required": true, "schema": { "type": "string" } },
                        { "in": "query", "name": "page", "schema": { "type": "integer" } }
                    ],
                    "responses": {
                        "200": { "description": "Success" },
                        "500": { "description": "Error" }
                    }
                }
            }
        }
    },
    apis: [],
};

module.exports = swaggerJSDoc(options);