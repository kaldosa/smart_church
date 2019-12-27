package com.laonsys.springmvc.extensions.controller;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import com.laonsys.smartchurch.domain.SearchParam;
import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.Entity;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;
import com.laonsys.springmvc.extensions.model.ViewType;
import com.laonsys.springmvc.extensions.service.GenericService;
import com.laonsys.springmvc.extensions.validation.groups.Update;

/**
 * CRUD(Create, Read, Update, Delete) 액션을 구현한 제너릭 추상클래스 
 * 
 * <p>웹에서 사용자에 의해 일어나는 일반적인 액션은 CRUD 액션이다. (예로, 게시판 목록을 보고, 특정 게시물을 보고, 글을 작성하고, 수정, 삭제하는 등) 
 * 이와 같이 CRUD 액션들은 또한 보통 하나의 entity 와 관련이 있다. (물론 하나 이상일 때도 있지만) 
 * 하나의 entity 에 대한 CRUD 기능을 RESTful(?) 하게 동작하는 제너릭한 추상 클래스를 구현한다.
 * 
 * <p>AbstractCrudController 는 뷰 이름을 결정하기 위한  {@link ViewNameResolver}  와 
 * 구상 controller 에 pre/post 처리 기회를 주기 위한 인터셉터  {@link PreInterceptor} ,  {@link PostInterceptor} 
 * 그리고 controller 의 관심대상  {@link Entity} , 그에 대한 비즈니스 로직을 담당하는  {@link GenericService}  로 이루어져 있다. 
 * 
 * <p>HTTP method 에 따른 CRUD 동작
 * <ul>
 * <li>GET method : 목록보기, 디테일 뷰, 작성 폼, 수정 폼 
 * <li>POST method : 작성 폼 submit 
 * <li>PUT method : 수정 폼 submit 
 * <li>DELETE method : 삭제 
 * </ul>
 * 
 * 대부분의 브라우저는 GET, POST 만 지원한다. 이외의 method 를 사용하기 위해 Spring MVC 는 POST method 에 
 * hidden form field (<code>_method</code>) 를 추가하여 실제 HTTP method 로 변환해주는 {@link HiddenHttpMethodFilter}  를 제공한다.
 * 
 * @author  kaldosa
 * @param  < T  >  controller에서 처리할 model
 * @param  < S  >  model 에 대한 CRUD service
 * @see Entity
 * @see GenericService
 * @see ViewNameResolver
 * @see PreInterceptor
 * @see  PostInterceptor
 */
public abstract class AbstractCrudController<T extends Entity<? super T>, S extends GenericService<T>> {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * <code>serviceClass</code>를 통해 service Bean을 가져오기 위한 application context
     */
    @Autowired ApplicationContext applicationContext;
    
    /**
     * CRUD 동작후 view 를 결정하기 위한 resolver
     * @see ViewNameResolver
     * @see  DefaultViewNameResolver
     * @uml.property  name="viewNameResolver"
     * @uml.associationEnd  
     */
    @Autowired ViewNameResolver<T> viewNameResolver;

    /**
     * CRUD method 를 수행하기 전에 해야할 작업들이 있을 경우 사용하는 인터셉터
     * @see PreInterceptor
     * @see  PreInterceptorAdapter
     * @uml.property  name="preInterceptor"
     * @uml.associationEnd  
     */
    @Autowired PreInterceptor<T> preInterceptor;
    
    /**
     * CRUD method 를 수행한 후에 해야할 작업들이 있을 경우 사용하는 인터셉터
     * @see PostInterceptor
     * @see  PostInterceptorAdapter
     * @uml.property  name="postInterceptor"
     * @uml.associationEnd  
     */    
    @Autowired PostInterceptor<T> postInterceptor;
    
    /**
     * entity 에 대한 타입 추론을 위해 사용
     */
    Class<T> entityClass;
    
    /**
     * service 타입 추론을 위해 사용
     */
    Class<S> serviceClass;
    
    /**
     * 클레스 레벨의  {@link RequestMapping}  url
     * @uml.property  name="baseUrl"
     */
    String baseUrl;

    /**
     * {@link ViewNameResolver} 가 사용하는 뷰 root 폴더 (default : baseUrl)
     * 
     * <p>viewRoot 를 바꾸기 위해선 <b>반드시</b> {@link #setUp()} 에서 설정해야 한다.
     * <pre>
     * {@code
     * void setUp() {
     *      
     *      setViewRoot("/test"); // view 파일을 test 폴더에서 찾는다.
     * }
     * </pre>
     */
    String viewRoot;
    
    /**
     * entity 에 대한 CRUD service
     * @uml.property  name="service"
     */
    S service;
    
    private Map<String, Map<String, Object>> additionConditions;
    
    /**
     * 디폴트 생성자
     * 
     * <p>구상 클래스에 설정된 클레스 레벨의 {@link RequestMapping} 정보를 가져와 <code>baseUrl</code> 에 설정하고
     * {@link ParameterizedType}을 통해 entity 와 entity service 에 대한 타입을 추론한다.
     */
    @SuppressWarnings("unchecked")
    public AbstractCrudController() {
        RequestMapping rm = this.getClass().getAnnotation(RequestMapping.class);
        this.baseUrl = rm.value()[0];
        
        ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
        serviceClass = (Class<S>) paramType.getActualTypeArguments()[1];
    }

    /**
     * entity 에 대한 타입정보를 통해 entity 인스턴스를 생성
     * 
     * @return entity 의 인스턴스
     */
    protected T newEntity() {
        getClass().getGenericSuperclass();
        try {
            return entityClass.newInstance();
        }
        catch (InstantiationException e) {
            // Shouldn't happen
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            // Shouldn't happen
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 추상 클래스의 생성자 호출후 해야할 작업들을 수행하는 method
     * 
     * <p><ul>
     * <li><b>serivce bean 설정</b>
     * <p>제너릭 타입의 service 는 애노테이션 {@link Autowired} 를 통해 주입이되지 않는다.
     * {@link GenericService} 의 구현체가 여러개이기 때문에 어느것을 주입해야 될지 spring 이 알수 없기 때문이다.
     * 따라서, entity 의 타입 추론을 통해 특정 entity 에 대한 service 구현체를 {@link ApplicationContext}로
     * service 빈을 가져온다.
     * <li><b>addition condtions</b>
     * <p>각 CRUD method 에서 사용할 부가적인 condition 을 저장하기 위한 Map을 생성한다.
     * <li><b>viewRoot 설정</b>
     * <p>뷰 root 폴더를 설정한다. default 값으로 baseUrl 이 설정된다.
     * <li><b>setUp method 호출</b>
     * <p>구상 클래스에서 추가적인 설정이 필요한 경우, {@link #setUp} method 를 오버라이딩하여 사용한다.
     * </ul>
     *
     */
    @PostConstruct
    public void postConstruct() {
        this.service = applicationContext.getBean(serviceClass);
        
        additionConditions = new HashMap<String, Map<String, Object>>();
        
        viewRoot = baseUrl;
        
        setUp();
    }

    /**
     * 추가적인 설정이 필요할 경우, 구상 클래스는 이 메소드를 오버라이딩 하여 작업한다.
     * 
     * @see #postConstruct
     */
    public void setUp() {}
    
    /**
     * service 에 대한 getter method
     * @return  entity 에 대한 CRUD service
     * @uml.property  name="service"
     */
    public S getService() {
        return service;
    }

    /**
     * 뷰 root 폴더 설정
     * @param viewRoot  view root 폴더
     * @uml.property  name="viewRoot"
     */
    public void setViewRoot(String viewRoot) {
        this.viewRoot = viewRoot;
    }
    
    /**
     * view name resolver 설정
     * @param viewNameResolver  view name resolver
     * @see  DefaultViewNameResolver
     * @uml.property  name="viewNameResolver"
     */
    public void setViewNameResolver(ViewNameResolver<T> viewNameResolver) {
        this.viewNameResolver = viewNameResolver;
    }
    
    /**
     * view name resolver 반환
     * @return  view name resolver
     * @see  DefaultViewNameResolver
     * @uml.property  name="viewNameResolver"
     */
    public ViewNameResolver<T> getViewNameResolver() {
        return viewNameResolver;
    }

    /**
     * CRUD 전처리기 인터셉터 설정
     * @param preInterceptor  CRUD 전처리기 인터셉터
     * @see  PreInterceptorAdapter
     * @uml.property  name="preInterceptor"
     */
    public void setPreInterceptor(PreInterceptor<T> preInterceptor) {
        this.preInterceptor = preInterceptor;
    }
    
    /**
     * CRUD 후처리기 인터셉터 설정
     * @param postInterceptor  CRUD 후처리기 인터셉터
     * @see  PostInterceptorAdapter
     * @uml.property  name="postInterceptor"
     */
    public void setPostInterceptor(PostInterceptor<T> postInterceptor) {
        this.postInterceptor = postInterceptor;
    }
    
    /**
     * base url 반환
     * @return  base url
     * @uml.property  name="baseUrl"
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    
    /**
     * CRUD method 에서 사용할 부가적인 condition 을 추가한다.
     * 
     * @param method CRUD method name (list, view, create, edit, delete)
     * @param condition service 에서 사용할 condition
     */
    public void addCondition(String method, Map<String, Object> condition) {
        additionConditions.put(method, condition);
    }
    
    /**
     * 특정 CRUD method 에 해당하는 condition 을 반환한다.
     * 
     * <p>만약 {@link #additionConditions} 에 추가적인 condition 이 있을 경우, condition 에 추가하여 반환한다.
     * 
     * @param method CRUD method name (list, view, create, edit, delete)
     * @return condition
     */
    public Map<String, Object> getCondition(String method) {
        Map<String, Object> condition = new HashMap<String, Object>();
        
        if(additionConditions.containsKey(method)) {
            Map<String, Object> addition = additionConditions.get(method);
            for(Map.Entry<String, Object> entry : addition.entrySet()) {
                condition.put(entry.getKey(), entry.getValue());
            }
        }
        
        return condition;
    }

    // ===============================================================================================
    // list
    // ===============================================================================================

    /**
     * 
     * @param searchParam
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getList(SearchParam searchParam, Model model) {
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.LIST, entityClass, null);
    }
    
    /**
     * 목록보기
     * 
     * <p>service 를 통해 조건을 만족하는 entity 목록을 가져온다. service 는 부가적으로 페이징을 위해 
     * {@link Paginate} 를 condition 에 저장한다. 최종적으로 <code>list</code> 라는 이름으로
     * entity 목록을, <code>paginate</code> 이름으로 {@link Paginate} 를 {@link Model} 에 담에 view 에 전달한다. 
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : GET
     * <li>URL 패턴 : 구상클래스의 클레스 레벨의 {@link RequestMapping} 즉, baseUrl (ex, /business/notices : 되도록 복수형을 쓴다.)
     * <li>뷰 이름 : viewRoot + "/list" + entity class SimpleName 조합 (ex, /business + "/list" + Notice = /business/listNotice)
     * </ul>
     * 
     * @param page page 인덱스
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름
     */
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String getList(@RequestParam(value = "page", defaultValue = "1") long page, Model model) {
//        
//        preInterceptor.preList(page, model);
//        
//        Condition condition = getCondition("list");
//        condition.put("page", page);
//        
//        List<T> list = getList(condition);
//
//        model.addAttribute("list", list);
//        model.addAttribute("paginate", condition.get("paginate"));
//        
//        logger.debug("{}", list);
//        logger.debug("{}", condition.get("paginate"));
//        
//        postInterceptor.postList(page, model);
//        
//        return viewNameResolver.viewNameResolver(viewRoot, ViewType.LIST, entityClass, null);
//    }
    
//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public String getList(@ModelAttribute Paginate paginate, Model model) {
//        
//        preInterceptor.preList(paginate, model);
//        
//        List<T> list = service.selectList(paginate);
//
//        model.addAttribute("list", list);
//        
//        logger.debug("{}", list);
//        logger.debug("{}", paginate);
//        
//        postInterceptor.postList(paginate, model);
//        
//        return viewNameResolver.viewNameResolver(viewRoot, ViewType.LIST, entityClass, null);
//    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getList(@WebQuery QueryParam queryParam, Model model) {
        
        preInterceptor.preList(queryParam, model);
        
        List<T> list = service.selectList(queryParam);
        
        model.addAttribute("list", list);
        model.addAttribute("paginate", queryParam.getPaginate());
        model.addAttribute("queryParam", queryParam);
        
        postInterceptor.postList(queryParam, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.LIST, entityClass, null);
    }
    
//    @RequestMapping(value = "", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
//    @ResponseBody
//    public List<T> getList(@RequestParam(value = "page", defaultValue = "1") int page) {
//        Condition condition = getCondition("list");
//        condition.put("page", page);
//        
//        return getList(condition);
//    }
//    
//    public List<T> getList(Condition condition) {
//        return service.findAll(condition);
//    }

    // ===============================================================================================
    // view
    // ===============================================================================================

    /**
     * 디테일 정보 보기
     * 
     * <p>service 를 통해 entity id 및 부가적인 조건을 만족하는 entity 의 디테일 정보를 가져온다.
     * entity 의 디테일 정보를 <code>entity</code> 라는 이름으로 {@link Model} 에 담에 view 에 전달한다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : GET
     * <li>URL 패턴 : baseUrl/{entityId} , entity id 를 path param 으로 받는다. (ex, /business/notices/12)
     * <li>뷰 이름 : viewRoot + "/view" + entity class SimpleName 조합 (ex, /business + "/view" + Notice = /business/viewNotice)
     * </ul>
     * 
     * @param entityId entity id
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름
     */
    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public String getView(@PathVariable int entityId, @ModelAttribute Paginate paginate, Model model) {
        
        preInterceptor.preView(entityId, model);
        
        Map<String, Object> condition = getCondition("view");
        condition.put("id", entityId);
                
        T entity = getDetails(condition, model);
        model.addAttribute("entity", entity);
        
        logger.debug("{}", entity);
        
        if(entity == null) {
            return viewNameResolver.viewNameResolver(viewRoot, ViewType.REDIRECT, entityClass, baseUrl);
        }
        //updateHits(entity);
        
        postInterceptor.postView(entityId, entity, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.VIEW, entityClass, null);
    }

    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET, consumes = {"application/json", "application/xml"})
    @ResponseBody
    public T getView(@PathVariable int entityId, HttpSession session) {
        return service.selectOne(entityId);
    }
    
    public T getDetails(Map<String, Object> condition, Model model) {
//        return service.findOne(condition);
        return null;
    }
    
    protected void updateHits(T entity) {
//        service.updateHits(entity);
    }
    
    // ===============================================================================================
    // create
    // ===============================================================================================
    
    /**
     * create 폼
     * 
     * <p>entity 에 대한 작성 폼 뷰를 가져온다. {@link #newEntity()} 를 통해 새로운 entity 인스턴스를 
     * {@link Model} 에 담에 view 에 전달한다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : GET
     * <li>URL 패턴 : baseUrl/new (ex, /business/notices/new)
     * <li>뷰 이름 : viewRoot + "/create" + entity class SimpleName 조합 (ex, /business + "/create" + Notice = /business/createNotice)
     * </ul>
     * 
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getCreateForm(Model model) {
        
        T entity = newEntity();

        model.addAttribute("entity", entity);
        
        preInterceptor.preCreate(entity, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.CREATE, entityClass, null);
    }
    
    /**
     * create 폼 submit
     * 
     * <p>POST 를 통해 entity 에 form data 를 binding 을 한다. binding 된 entity 를 service 를 통해 DB 에 저장을 하고, 목록보기로
     * 리다이렉트를 한다. 만약 binding error 가 있을 경우 다시 binding result 를 가지고 create form 뷰로 되돌린다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : POST
     * <li>URL 패턴 : baseUrl (ex, /business/notices)
     * <li>뷰 이름 : 성공시 목록보기로 리다이렉트, 실패시 binding result 를 가지고 create form 뷰로 되돌린다.
     * </ul>
     * 
     * @param entity form data 로부터 binding 된 entity
     * @param result binding result
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postCreateForm(@Validated({Default.class}) @ModelAttribute("entity") T entity, BindingResult result, Model model) {
        
        logger.debug("{}", entity);
        
        if(result.hasErrors()) {
            
            logger.debug("{}", "================> validation error");
            logger.debug("{}", result.toString());
            
            preInterceptor.preCreate(entity, model);
            return viewNameResolver.viewNameResolver(viewRoot, ViewType.CREATE, entityClass, null);
        }
        
        service.create(entity);
        
        logger.debug("{}", "================> success create!!");
        
        postInterceptor.postCreate(entity, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.REDIRECT, entityClass, baseUrl);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {"application/json", "application/xml"})
    @ResponseBody
    public String postCreateForm(@Valid @ModelAttribute("entity") T entity, BindingResult result) {
        if(result.hasErrors()) {
            return baseUrl + "/new";
        }
        
        service.create(entity);
        
        return "redirect:" + baseUrl + "/list";
    }
    
    // ===============================================================================================
    // update
    // ===============================================================================================

    /**
     * edit 폼
     * 
     * <p>entity 에 대한 수정 폼 뷰를 가져온다. path param 으로 전달된 entity id 를 통해 수정할 entity 를 가져와서  
     * {@link Model} 에 담에 view 에 전달한다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : GET
     * <li>URL 패턴 : baseUrl/{entityId}/edit (ex, /business/notices/12/edit)
     * <li>뷰 이름 : viewRoot + "/edit" + entity class SimpleName 조합 (ex, /business + "/edit" + Notice = /business/editNotice)
     * </ul>
     * 
     * @param entityId 수정할 entity id
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "/{entityId}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable int entityId, Model model) {
        
        T entity = service.selectOne(entityId);

        model.addAttribute("entity", entity);
        
        preInterceptor.preUpdate(entity, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.EDIT, entityClass, null);
    }
    
    /**
     * edit 폼 submit
     * 
     * <p>PUT 를 통해 entity 에 form data 를 binding 을 한다. binding 된 entity 를 service 를 통해 DB 에 update 을 하고, 목록보기로
     * 리다이렉트를 한다. 만약 binding error 가 있을 경우 다시 binding result 를 가지고 edit form 뷰로 되돌린다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : PUT
     * <li>URL 패턴 : baseUrl/{entityId} (ex, /business/notices/12)
     * <li>뷰 이름 : 성공시 목록보기로 리다이렉트, 실패시 binding result 를 가지고 edit form 뷰로 되돌린다.
     * </ul>
     * 
     * @param entityId 수정할 entity id
     * @param entity form data 로부터 binding 된 entity
     * @param result binding result
     * @param model view 에서 사용하는 model map
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "/{entityId}", method = RequestMethod.PUT)
    public String putEditForm(@PathVariable Integer entityId,
                              @Validated({Update.class}) @ModelAttribute("entity") T entity,
                              BindingResult result,
                              Model model) {
        
        entity.setId(entityId);
        
        logger.debug("{}", entity);
        
        if(result.hasErrors()) {
            
            logger.debug("{}", "================> validation error");
            logger.debug("{}", result.toString());
            
            preInterceptor.preUpdate(entity, model);
            
            return viewNameResolver.viewNameResolver(viewRoot, ViewType.EDIT, entityClass, null);
        }
        
        service.update(entity);
        
        logger.debug("{}", "================> success update!!");
        
        postInterceptor.postUpdate(entity, model);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.REDIRECT, entityClass, baseUrl);
    }
    
    // ===============================================================================================
    // delete
    // ===============================================================================================
    
    /**
     * 삭제
     * 
     * <p> path param 으로 넘어온 entity id 로 해당 entity 를 DB 에서 삭제한다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : DELETE
     * <li>URL 패턴 : baseUrl/{entityId} (ex, /business/notices/12)
     * <li>뷰 이름 : 목록보기로 리다이렉트
     * </ul>
     * 
     * @param entityId 삭제할 entity id
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public String delete(@PathVariable int entityId) {
        service.delete(entityId);
        
        return viewNameResolver.viewNameResolver(viewRoot, ViewType.REDIRECT, entityClass, baseUrl);
    }
    
    /**
     * 삭제
     * 
     * <p>여러개를 삭제할 경우, POST 로 entity id 를 배열형태로 받아서 삭제한다.
     * 
     * <p><b>RESTful pattern</b>
     * <ul><li>HTTP method : POST
     * <li>URL 패턴 : baseUrl/delete (ex, /business/delete)
     * <li>뷰 이름 : 목록보기로 리다이렉트
     * </ul>
     * 
     * @param entityIds 삭제할 entity id 배열
     * @return 뷰페이지 이름 또는 리다렉트
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("deleteItems") int[] entityIds) {
        service.delete(entityIds);

        return viewNameResolver.viewNameResolver(viewRoot, ViewType.REDIRECT, entityClass, baseUrl);
    }    
}
