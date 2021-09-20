import argparse
import requests

if __name__ == '__main__':

    parser = argparse.ArgumentParser(description='Smoke test for Zeal.')
    parser.add_argument('--ip', required=True, help='IP address of Zeal')
    parser.add_argument('--port', required=True, help='Port of Zeal')
    args = parser.parse_args()

    address = '{}:{}'.format(args.ip, args.port)
    url = 'http://{}/article'.format(address)
    response = requests.get(url)

    assert response.status_code == 200, 'Expected status to be successful'
    
    print('')
    print('Zeal is successfully running at {}'.format(address))
    print('')
