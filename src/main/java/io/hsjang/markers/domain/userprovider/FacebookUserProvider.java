package io.hsjang.markers.domain.userprovider;

import java.util.List;

import lombok.Data;

@Data
public class FacebookUserProvider implements UserProvider {
/*

accessToken: EAAGigL47hNEBAD0hEUwlbWDIWuGv3w9bQUhLC9KLqhb0knkMRZCnGEfpZBaYu1jn0kub8FEk0VqT7NCWIvkeASe2i8ohENiaXTuIHhbj8A6eWHE7IBCnZCTTYEEY6TMZCgriq7WTrLNs0d8FZBntnr4M3KZBPvV5gG4xdrUMjESGT7xX1Y5uZCLlCL7D2kUQCMxiZAxzEAODBwZDZD
userID: 1940361676003655
expiresIn: 6290
signedRequest: L9lOjqS0-uIo_xcDHkUhLBUyFytIGuXlnfvWV8XWPko.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImNvZGUiOiJBUUJGRnBPdWhTYWFqUFhnbFNZZy1PTEtPTlU0VDN0TTZfMGhMNGo0VWNRTm9nS1hndGNzRjdYVUxWdlVfMHkyeV9Za0N5R2QwWVhhTDhCRUxMOXZnN20zZ1dKN2dtRExyNDNoQWRNTGd0MEcxLVlVY0pHbUdSLXlpbFlaaGFjU0VSTmt1Snk1S01nMUZFVDVYc090ZG9SOExyVURIcU9yRlAzeGRUbXRVQU0xRFpkZ2xsLW1aMkh6Vko2eHctQ0h1SFpnZElHTmJqUU5sbVBQbTJSN2tidGE2eXZ1aUhBa2VraE1qWEg2eTJzRVM4X0FVeXdMTEpPSGZnYnI2aUpVWFlFTDlBZE56bVRWX0ZYVnFBVXNlaEVSTUpyQllJdjNXajhMQURSeE1PR2U4X3EtTXp2NldYSExmY3JWZV82NFN1aUN6em1fYjRQT0JIbWl3R2JCWnBrNiIsImlzc3VlZF9hdCI6MTUzMzIyNjUxMCwidXNlcl9pZCI6IjE5NDAzNjE2NzYwMDM2NTUifQ
reauthorize_required_in: 7775999

accessToken - 앱 사용자의 액세스 토큰이 포함되어 있습니다.
expiresIn - 토큰이 만료되어 갱신해야 하는 UNIX 시간을 표시합니다.
signedRequest - 앱 사용자에 대한 정보를 포함하는 서명된 매개변수입니다.
userID - 앱 사용자의 ID입니다.

 */
	String accessToken;
	String userID;
	int expiresIn;
	String signedRequest;
	int reauthorize_required_in;
	
	@Override
	public String getUserId() {
		return this.userID;
	}
	@Override
	public String getName() {
		return "name";
	}
	@Override
	public String getImage() {
		return "image";
	}
	@Override
	public List<String> getAuths() {
		return null;
	}
	
}
