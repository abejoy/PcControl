import axios from 'axios'

export class PiDataService {
    url = 'http://www.abrahamjoys.com'

    // url = '/'

    press() {
        return axios.get(this.url + '/press')
    }
}