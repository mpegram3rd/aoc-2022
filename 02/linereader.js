const fs = require('node:fs');
const readline = require('node:readline');

module.exports = (function() {
    this.process = async (fileName, lineProcessor, accumulator) => {
        const fileStream = fs.createReadStream(fileName);

        const rl = readline.createInterface({
            input: fileStream,
            crlfDelay: Infinity,
        });
        // Note: we use the crlfDelay option to recognize all instances of CR LF
        // ('\r\n') in input.txt as a single line break.


        // Each line in input.txt will be successively available here as `line`.
        for await (const line of rl)
            lineProcessor(line, accumulator);
        console.log('ProcessLineByLine finished');
    }

    return this;
})();
