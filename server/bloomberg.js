const app = require('express')();
const http = require('http').createServer(app);
const io = require('socket.io')(http);
const csv = require('csv-parser');
const fs = require('fs');

let csvArray = [];


fs.createReadStream('bbg.csv')
    .pipe(csv({
        headers: false
    }))
    .on('data', row => {    
        csvArray.push(row);
    })
    .on('end', () => {
        console.log('CSV file successfully processed');
        console.log(csvArray)

        io.on('connection', async (socket) => { 
    
            for (let i = 0; i < csvArray.length; i++) {
                socket.emit('news', csvArray[i]);
                await new Promise(resolve => setTimeout(resolve, 1000))
            }
            
        
        });
    })

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/client.html');
});


http.listen(3000, () => {
    console.log('listening on *:3000');
});


