const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 5500;

const MIME_TYPES = {
    '.html': 'text/html',
    '.js': 'text/javascript',
    '.css': 'text/css',
    '.json': 'application/json',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.svg': 'image/svg+xml',
};

const server = http.createServer((req, res) => {
    // Handle the save endpoint to write back to data.js
    if (req.method === 'POST' && req.url === '/save') {
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        req.on('end', () => {
            try {
                const data = JSON.parse(body);
                // Format the JSON data into a JavaScript module export
                const jsContent = `export const defaultState = ${JSON.stringify(data, null, 4)};\n`;
                
                // Write the updated data to data.js
                fs.writeFileSync(path.join(__dirname, 'data.js'), jsContent, 'utf8');
                
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({ status: 'success' }));
            } catch (err) {
                console.error("Error saving to data.js:", err);
                res.writeHead(500, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({ status: 'error', message: err.message }));
            }
        });
        return;
    }

    // Serve static files
    let filePath = req.url === '/' ? '/javaLearningContext.html' : req.url;
    // Remove query parameters if any exist
    filePath = filePath.split('?')[0];

    const extname = path.extname(filePath);
    const contentType = MIME_TYPES[extname] || 'text/plain';

    const absolutePath = path.join(__dirname, filePath);

    fs.readFile(absolutePath, (err, content) => {
        if (err) {
            if (err.code === 'ENOENT') {
                res.writeHead(404);
                res.end('File not found');
            } else {
                res.writeHead(500);
                res.end(`Server error: ${err.code}`);
            }
        } else {
            res.writeHead(200, { 'Content-Type': contentType });
            res.end(content, 'utf-8');
        }
    });
});

server.listen(PORT, () => {
    console.log(`\n=========================================`);
    console.log(`🚀 Tracker Server running at http://localhost:${PORT}`);
    console.log(`👉 Open http://localhost:${PORT}/javaLearningContext.html in your browser`);
    console.log(`💾 Changes will be automatically saved to data.js!`);
    console.log(`=========================================\n`);
});
