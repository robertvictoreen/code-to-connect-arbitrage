const app = require('express')();
const http = require('http').createServer(app);
const io = require('socket.io')(http);
const csv = require('csv-parser');
const fs = require('fs');

let csvArray = [];


fs.createReadStream('ebs.csv')
    .pipe(csv({
        headers: false
    }))
    .on('data', row => { 
        row.provider = 'ebs';
        csvArray.push(row);
        console.log(row);
    })
    .on('end', () => {
        console.log('CSV file successfully processed');

        io.on('connection', async (socket) => { 
            
            for (let i = 0; i < csvArray.length; i++) {
                socket.emit('ebs', csvArray[i]);
                await new Promise(resolve => setTimeout(resolve, 1000))
            }
            
        
        });
    });

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/client.html');
});


http.listen(4000, () => {
    console.log('listening on *:4000');
});


