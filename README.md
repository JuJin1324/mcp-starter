# 🔧 MCP 기본 사용 팁

### 1. MCP 도구 종류와 활용법

**📁 파일 시스템 도구**

✅ 효과적 사용법:

- 프로젝트 전체 구조 파악: "find . -name '*.java' | grep -E '(Controller|Service|Repository)'"
- 특정 파일 내용 확인: 파일 경로를 정확히 지정
- 여러 파일 동시 수정: 관련 파일들을 한 번에 열어서 일관성 유지

❌ 피해야 할 실수:

- 너무 큰 디렉토리 전체를 한 번에 읽으려 하기
- 바이너리 파일(.class, .jar) 읽으려 하기

🔑 **Notion API Authentication 설정 가이드**

**1️⃣ Notion Integration 생성**

```bash
# 단계별 가이드
1. Notion 개발자 페이지 접속
   └─ https://www.notion.so/my-integrations

2. 새 Integration 생성
   └─ "New integration" 클릭
   └─ Basic information 입력
```

**Integration 설정 세부사항:**
- **Name**: `MCP Server Integration` (또는 프로젝트명)
- **Associated workspace**: 사용할 워크스페이스 선택
- **Capabilities**: 
  - ✅ Read content
  - ✅ Update content  
  - ✅ Insert content
  - ❌ No user information (보안상 비활성화 권장)

**2️⃣ API Token 생성 및 관리**

```bash
# Token 형태 예시
ntn_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

**🔐 보안 Best Practices:**
- Token을 환경변수로 관리 (`NOTION_API_TOKEN`)
- `.env` 파일 사용 시 `.gitignore`에 포함
- 프로덕션 환경에서는 Secret Manager 사용

**3️⃣ Claude Desktop 설정**

```json
{
  "mcpServers": {
    "notionApi": {
      "command": "npx",
      "args": ["-y", "@notionhq/notion-mcp-server"],
      "env": {
        "OPENAPI_MCP_HEADERS": "{\"Authorization\": \"Bearer ntn_YOUR_TOKEN_HERE\", \"Notion-Version\": \"2022-06-28\"}"
      }
    }
  }
}
```

**4️⃣ 페이지 권한 설정**

Notion에서 Integration이 접근할 페이지/데이터베이스마다:
```bash
1. 페이지 우상단 "..." 메뉴 클릭
2. "Add connections" 선택  
3. 생성한 Integration 선택
4. "Confirm" 클릭
```

**5️⃣ 연결 테스트**

```bash
# MCP 서버 테스트 방법
1. Claude Desktop 재시작
2. 새 대화에서 Notion 관련 명령어 실행
3. "List my Notion pages" 또는 "Show my databases" 시도
```

**⚠️ 트러블슈팅**

| 문제 | 해결방법 |
|------|----------|
| `401 Unauthorized` | Token 재확인, 페이지 권한 설정 확인 |
| `403 Forbidden` | Integration이 해당 페이지에 권한이 없음 |
| `Connection refused` | Claude Desktop 재시작 필요 |
| `MCP server not found` | npm 캐시 정리: `npm cache clean --force` |

---

🐙 **GitHub MCP Server 설정 가이드**

**1️⃣ GitHub Personal Access Token 생성**

```bash
# GitHub Token 생성 단계
1. GitHub 로그인 후 Settings 접속
   └─ https://github.com/settings/tokens

2. Personal access tokens 선택
   └─ "Tokens (classic)" 또는 "Fine-grained tokens" 선택
   └─ "Generate new token" 클릭
```

**권한 설정 (Scopes):**
- **Classic Token 권한:**
  - ✅ `repo` - 전체 저장소 접근
  - ✅ `read:org` - 조직 정보 읽기
  - ✅ `read:user` - 사용자 정보 읽기
  - ✅ `user:email` - 이메일 주소 접근
  - ✅ `notifications` - 알림 관리
  - ✅ `write:discussion` - 토론 작성

- **Fine-grained Token 권한:**
  - ✅ Contents: Read and write
  - ✅ Issues: Read and write  
  - ✅ Pull requests: Read and write
  - ✅ Metadata: Read

**2️⃣ Token 보안 관리**

```bash
# Token 형태 예시
# Classic: ghp_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
# Fine-grained: github_pat_XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

**🔐 보안 Best Practices:**
- Token 만료 기간 설정 (최대 1년)
- 환경변수로 관리 (`GITHUB_TOKEN`)
- Repository별 세분화된 권한 부여
- 정기적인 Token 순환(Rotation)

**3️⃣ Claude Desktop 설정**

```json
{
  "mcpServers": {
    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "your_github_token_here"
      }
    }
  }
}
```

**4️⃣ MCP Server 기능별 활용**

**📊 Repository 관리:**
```bash
# 활용 예시
- "List my repositories"
- "Show recent commits in [repo-name]"
- "Create a new repository named [name]"
- "Search repositories with [keyword]"
```

**🔍 Issue & PR 관리:**
```bash
# Issue 관리
- "List open issues in [repo]"
- "Create an issue in [repo] with title [title]"
- "Assign issue #123 to @username"

# Pull Request 관리  
- "List open PRs in [repo]"
- "Review PR #456 in [repo]"
- "Merge PR #789 with squash strategy"
```

**📈 Code Analysis:**
```bash
# 코드 분석
- "Show file contents of [file-path] in [repo]"
- "Search for [pattern] in [repo] codebase"
- "List recent changes in [directory]"
```

**5️⃣ 고급 설정 및 최적화**

**멀티 계정 설정:**
```json
{
  "mcpServers": {
    "github-personal": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "personal_token",
        "GITHUB_API_URL": "https://api.github.com"
      }
    },
    "github-enterprise": {
      "command": "npx", 
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "enterprise_token",
        "GITHUB_API_URL": "https://api.github.enterprise.com"
      }
    }
  }
}
```

**⚠️ 트러블슈팅**

| 문제 | 해결방법 |
|------|----------|
| `401 Bad credentials` | Token 재확인, 권한 스코프 점검 |
| `403 rate limit exceeded` | Token 사용량 확인, 여러 Token 로테이션 |
| `404 Not Found` | Repository 접근 권한 확인 |
| `422 Validation failed` | 입력 데이터 형식 검증 |
| `Server connection failed` | GitHub API 상태 확인, 네트워크 점검 |

**🔧 성능 최적화 팁:**
- GraphQL API 활용으로 필요한 데이터만 조회
- Webhook 설정으로 실시간 업데이트 구현
- 캐싱 전략으로 API 호출 최소화
- Batch 요청으로 다중 작업 효율화

---

## 🚀 바이브 코딩 완전 가이드

### 1. 프로젝트 시작 단계

**📋 체크리스트 방식 접근**

✅ Phase 1: 프로젝트 이해하기

- 기존 코드 스타일 파악
- 아키텍처 패턴 확인
- 사용 중인 라이브러리 목록
- 테스트 전략 파악

### 2. 바이브 코딩 단계별 전략

**🎯 1단계: 컨텍스트 설정**

```
안녕하세요! 새로운 기능을 개발하기 전에 몇 가지 확인하고 싶어요.
1. 현재 프로젝트 구조를 파악해주세요
2. 제가 선호하는 개발 스타일을 물어봐주세요
3. 이 기능과 관련된 기존 코드가 있는지 찾아주세요.
4. 매 단계마다 다음의 지시를 따라주세요.
- 네이밍 컨벤션 일관성
- 비즈니스 로직과 기술적 로직 분리
- SOLID 원칙 준수
- 적절한 추상화 레벨
- 테스트 가능한 구조
```

**🔍 2단계: 스타일 가이드 설정**

```jsx
// AI가 물어봐야 할 질문들 혹은 개발자가 미리 AI 에게 입력해야할 질문들
아키텍처: "레이어드 아키텍처 vs 헥사고날 아키텍처?",
테스트: "통합테스트 vs 단위테스트 중심?",
에러 핸들링: "Custom Exception vs Standard Exception?",
로깅: "상세 로깅 vs 간결한 로깅?",
검증: "@Valid vs 수동 검증?",
트랜잭션: "@Transactional 세분화 정도?"
```

**⚡ 3단계: 점진적 구현**

단계별 구현 방식:

- "먼저 도메인 모델만 만들어주세요" → 확인 → 피드백 → 수정
- "이제 Repository 인터페이스를 만들어주세요" → 확인 → 피드백 → 수정
- "Service 로직을 구현해주세요" → 확인 → 피드백 → 수정
- "Controller를 작성해주세요" → 확인 → 피드백 → 수정
- "테스트 코드를 만들어주세요" → 확인 → 피드백 → 수정

### 3. 바이브 코딩 고급 팁

**🎨 코드 품질 관리**

매 단계마다 확인사항:

- 네이밍 컨벤션 일관성
- 비즈니스 로직과 기술적 로직 분리
- SOLID 원칙 준수
- 적절한 추상화 레벨
- 테스트 가능한 구조

**🔄 피드백 루프 최적화**

```
효과적인 피드백 표현:
❌ "이거 별로야"
✅ "UserService에서 비즈니스 로직이 너무 많이 섞여있어요. 도메인 서비스로 분리해주세요"
❌ "다시 해줘"
✅ "Controller에서 @RequestBody 검증 로직을 별도 Validator 클래스로 추출해주세요"
```